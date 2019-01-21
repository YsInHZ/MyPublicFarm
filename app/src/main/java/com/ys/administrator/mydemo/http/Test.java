package com.ys.administrator.mydemo.http;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test {
    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(""),"wrd");
            //"r"/"rw"/"rws"/"rwd"其中一个
            // ("rws"/"rwd"共同点：和FileChannel.force(boolean)方法功能类似，都是为了更加高效；
            // "rws":除了读写，还要求每次更新文件内容和元数据都需要同步到设备的存储空间去；
            // "rwd"：仅仅是将文件内容写入到存储空间去)，否则报不合法参数异常。

            randomAccessFile.getFD();//返回RandomAccessFile对象不透明的文件流的描述对象。

            randomAccessFile.getChannel();//返回RandomAccessFile对象的唯一的文件通道。关于FileChannel类，请看这篇文章：[FileChannel类的说明]()
            // 此方法和FileChannel.position()方法返回的对象是一样的。position()返回的是对象的具有偏移量的文件指针(getFilePointer()获取)的文件通道。
            // 方法实现里面我们看到用到了同步，但是同步的却是整个类，势必造成效率低下。

            randomAccessFile.read();//读取文件数据的一个字节。一个字节以0--255的整数返回，返回-1表示到达文件的末尾。如果没有可用的输入流，此方法会阻塞。
            //这个方法的理解，就和InputStream类的read()方法的理解一样就OK了。

            randomAccessFile.read(new byte[20],10,20);//从文件中读取一定长度的字节，放入到参数1的数组中。返回-1表示已经到达文件末尾。抛出io异常，如果b为null抛出空指针异常和越界异常。

        } catch (FileNotFoundException e) {//new RandomAccessFile
            e.printStackTrace();
        } catch (IOException e) {//randomAccessFile.getFD();
            e.printStackTrace();
        }

    }
}
