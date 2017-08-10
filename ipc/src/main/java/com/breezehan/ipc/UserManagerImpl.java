package com.breezehan.ipc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  hands
 * Description  手动实现Binder
 * Date    2017/8/10 15:42
 * Version
 */

public class UserManagerImpl extends Binder implements IUserManager {
    private List<User> implList = new ArrayList<>();

    public UserManagerImpl() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static IUserManager asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IInterface iIn = obj.queryLocalInterface(DESCRIPTOR);
        if (iIn != null && iIn instanceof IUserManager) {
            return (IUserManager) iIn;
        }
        return new UserManagerImpl.Proxy(obj);
    }

    @Override
    public List<User> getUserList() throws RemoteException {
        //逻辑代码
        return implList;
    }

    @Override
    public void addUser(User user) throws RemoteException {
        //逻辑代码
        implList.add(user);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_addUser:
                data.enforceInterface(DESCRIPTOR);
                User _arg0;
                if (0 != data.readInt()) {
                    _arg0 = User.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                addUser(_arg0);
                reply.writeNoException();
                return true;
            case TRANSACTION_getUserList:
                data.enforceInterface(DESCRIPTOR);
                List<User> _result = getUserList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    static class Proxy implements IUserManager {
        private IBinder mRemote;

        public Proxy(IBinder remote) {
            this.mRemote = remote;
        }

        public java.lang.String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public List<User> getUserList() throws RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            List<User> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getUserList, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(User.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        @Override
        public void addUser(User user) throws RemoteException {
            Parcel _data = Parcel.obtain();
            Parcel _reply = Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                if (user != null) {
                    _data.writeInt(1);
                    user.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addUser, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }

        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }
    }
}
