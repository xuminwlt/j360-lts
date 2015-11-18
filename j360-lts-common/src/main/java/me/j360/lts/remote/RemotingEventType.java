package me.j360.lts.remote;

/**
 * 事件类型
 */
public enum RemotingEventType {
    CONNECT,
    CLOSE,
    READER_IDLE,
    WRITER_IDLE,
    ALL_IDLE,
    EXCEPTION
}
