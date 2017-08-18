// ISecurityCenter.aidl
package com.breezehan.ipc.binderpool;


interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}
