package me.j360.lts.remote.codec;


import me.j360.lts.common.extension.ExtensionLoader;
import me.j360.lts.remote.serialize.AdaptiveSerializable;
import me.j360.lts.remote.serialize.RemotingSerializable;

/**
 * @author Robert HG (254963746@qq.com) on 11/6/15.
 */
public abstract class AbstractCodec implements Codec {

    protected RemotingSerializable getRemotingSerializable(int serializableTypeId) {

        RemotingSerializable serializable = null;
        if (serializableTypeId > 0) {
            serializable = AdaptiveSerializable.getSerializableById(serializableTypeId);
            if (serializable == null) {
                throw new IllegalArgumentException("Can not support RemotingSerializable that serializableTypeId=" + serializableTypeId);
            }
        } else {
            serializable =
                    ExtensionLoader.getExtensionLoader(RemotingSerializable.class).getAdaptiveExtension();
        }
        return serializable;
    }

}
