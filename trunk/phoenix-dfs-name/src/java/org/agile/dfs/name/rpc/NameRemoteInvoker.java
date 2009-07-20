package org.agile.dfs.name.rpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.agile.dfs.core.exception.DfsException;
import org.agile.dfs.dao.IBatisTransactionFactory;
import org.agile.dfs.name.exception.NameNodeException;
import org.agile.dfs.name.service.BlockService;
import org.agile.dfs.name.service.BlockServiceImpl;
import org.agile.dfs.name.service.FileService;
import org.agile.dfs.name.service.FileServiceImpl;
import org.agile.dfs.name.service.SchemaService;
import org.agile.dfs.name.service.SchemaServiceImpl;
import org.agile.dfs.rpc.exception.ReflectOperateException;
import org.agile.dfs.rpc.piple.RpcRequest;
import org.agile.dfs.rpc.server.RpcInvoker;

public class NameRemoteInvoker implements RpcInvoker {
    protected static final IBatisTransactionFactory factory = new IBatisTransactionFactory();
    private static final String[] BLK_TRAN = new String[] { "commit", "locate" };
    private static final String[] FLE_TRAN = new String[] { "create*", "mk*", "delete*" };
    private static final String[] SCH_TRAN = new String[] { "build", "destroy" };
    private static final BlockService blockService = factory.findService(BlockServiceImpl.class, BLK_TRAN);
    private static final FileService fileService = factory.findService(FileServiceImpl.class, FLE_TRAN);
    private static final SchemaService schemaService = factory.findService(SchemaServiceImpl.class, SCH_TRAN);

    public Object invoke(RpcRequest req) throws ReflectOperateException {
        return this.invoke(req.getInterfaceClz(), req.getMethodName(), req.getArgs());
    }

    public Object invoke(String clzName, String methodName, Object[] args) throws ReflectOperateException {
        try {
            return doInvoke(clzName, methodName, args);
        } catch (InvocationTargetException te) {
            Throwable target = te.getTargetException();
            if (target instanceof NameNodeException) {
                throw (NameNodeException) target;
            } else {
                throw new DfsException(target);
            }
        } catch (Exception e) {
            throw new ReflectOperateException("Fail to reflect invoke " + clzName + ":" + methodName, e);
        }
    }

    public Object doInvoke(String clzName, String methodName, Object[] args) throws Exception {
        Object ins = getServiceInstance(clzName);
        Method method = getMethod(ins.getClass(), methodName, args);
        Object res = method.invoke(ins, args);
        return res;
    }

    private Object getServiceInstance(String clzName) {
        if (BlockService.class.getName().equals(clzName)) {
            return blockService;
        } else if (FileService.class.getName().equals(clzName)) {
            return fileService;
        } else if (SchemaService.class.getName().equals(clzName)) {
            return schemaService;
        } else {
            throw new DfsException("Not support service " + clzName);
        }
    }

    @SuppressWarnings("unchecked")
    private Method getMethod(Class clz, String methodName, Object[] args) {
        Method[] ms = clz.getDeclaredMethods();
        if (ms != null && ms.length != 0) {
            for (int i = 0, l = ms.length; i < l; i++) {
                if (methodName.equals(ms[i].getName())) {
                    if (ms[i].getParameterTypes().length == args.length) {
                        return ms[i];
                    }
                }
            }
        }
        throw new ReflectOperateException("Can't find method " + methodName + " from " + clz.getName());
    }
}
