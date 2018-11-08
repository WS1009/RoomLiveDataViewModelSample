/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.lzp.roomwordsample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.lzp.entity.Word;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;

    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    public void insert(Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... words) {
            mAsyncTaskDao.insert(words[0]);
            return null;
        }
    }

}
