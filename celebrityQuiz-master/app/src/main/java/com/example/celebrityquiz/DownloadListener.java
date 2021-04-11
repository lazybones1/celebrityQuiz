package com.example.celebrityquiz;

//DownloadListenr ineterface
public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}