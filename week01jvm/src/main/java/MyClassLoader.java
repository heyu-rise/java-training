import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author heyu
 * @date 2021/6/27
 */
public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        MyClassLoader classLoader = new MyClassLoader();
        Class<?> clazz = classLoader.findClass("Hello");
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(obj);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = name.replace('.', '/').concat(".xlass");
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
            assert inputStream != null;
            byte[] classByte = streamConvertByte(inputStream);
            classByte = decoderByte(classByte);
            return defineClass(name, classByte, 0, classByte.length);
        } catch (Exception e) {
            throw new RuntimeException("无法加载xlass文件");
        }
    }

    private byte[] streamConvertByte(InputStream inputStream) throws IOException {
        try {
            int length = inputStream.available();
            byte[] bytes = new byte[length];
            inputStream.read(bytes);
            return bytes;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] decoderByte(byte[] bytes) {
        byte[] bytesNew = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bytesNew[i] = (byte) (255 - bytes[i]);
        }
        return bytesNew;
    }

}
