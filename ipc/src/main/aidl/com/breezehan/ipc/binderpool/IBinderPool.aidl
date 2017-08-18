// IBinderPool.aidl
package com.breezehan.ipc.binderpool;


interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
