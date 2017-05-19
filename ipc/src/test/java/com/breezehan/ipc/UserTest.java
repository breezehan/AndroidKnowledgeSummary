package com.breezehan.ipc;

import android.os.Environment;
import android.util.Log;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.*;

/**
 * Description
 * Author  Administrator
 * Date    2017/5/12 11:29
 * Version
 */
public class UserTest {
    private static final String TAG = "UserTest";
    @Test
    public void serializable_test() throws Exception{
        User user = new User(1,"breezehan",true);
        File file = new File(Environment.getExternalStorageDirectory()+"cache.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(user);
        outputStream.close();

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        User userRead = (User)inputStream.readObject();
        Log.i(TAG, "serializable_test: "+userRead.getUserName());
    }
}