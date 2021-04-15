package com.example.celebrityquiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//DownloadTask
public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    // Declare static variables to switch between download results
    // 실행 결과값 flag
    private static final int TYPE_SUCCESS = 0;
    private static final int TYPE_FAILED = 1;
    private static final int TYPE_PAUSED = 2;
    private static final int TYPE_CANCELED = 3;

    private DownloadListener downloadListener;

    private boolean isCanceled = false;
    private boolean isPaused = false;
    //프로그래스바 상태
    private int lastProgress;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    //생성자
    DownloadTask(DownloadListener downloadListener, Context context) {
        this.downloadListener = downloadListener;
        this.context = context;
    }

    //taxk 순서 : task.execute -> onPreExcuted() ->  doInBackground -> onProgressUpdate() -> onPostExcute
    //백그라운드에서 실행중
    @Override
    protected Integer doInBackground(String... strings) {
        InputStream inputStream = null;
        RandomAccessFile accessFile = null;
        File file = null;

        //파일 생성
        try {
            long downloadedLength = 0;
            String downloadUrl = strings[0];
            String fileName = "myJson";

            // Save file in local directory
            File directory = context.getFilesDir();
            file = new File(directory, fileName);

            //파일이 존재하는 경우 삭제
            if(file.exists()) {
                file.delete(); // Clear available files
                downloadedLength = 0;
            }

            //문제수 반환받음
            long contentLength = getContentLength(downloadUrl);

            //문제 수가 0인경우 실패
            if(contentLength == 0) return TYPE_FAILED;

            //http 통신
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes = " + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();

            //파일 생성
            inputStream = Objects.requireNonNull(response.body()).byteStream();
            accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(downloadedLength); // omit the downloaded bytes // 포인터 위치값 설정

            byte[] b = new byte[1024];
            int total = 0;
            int len;

            while ((len = inputStream.read(b)) != -1) {
                if (isCanceled) {
                    return TYPE_CANCELED;
                } else if(isPaused) {
                    return TYPE_PAUSED;
                } else {
                    total += len;
                    accessFile.write(b, 0, len);
                    // calculate the percentages of downloaded part
                    int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                    publishProgress(progress);
                }
            }
            Objects.requireNonNull(response.body()).close();
            return TYPE_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
            }
                if (accessFile != null) {
                    accessFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        int progress = values[0];
        if (progress > lastProgress) {
            downloadListener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case TYPE_SUCCESS:
                downloadListener.onSuccess();
                break;
            case TYPE_FAILED:
                downloadListener.onFailed();
                break;
            case TYPE_PAUSED:
                downloadListener.onPaused();
                break;
            case TYPE_CANCELED:
                downloadListener.onCanceled();
            default:
                break;
        }
    }

    //문제수 가져오기
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            long contentLength = Objects.requireNonNull(response.body()).contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
}
