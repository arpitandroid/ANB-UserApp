package www.atmanirbharbharat.com.util;

public interface CallbackHandler<T>
{

    void onSuccess(T object);
    void onFailure(T object);

}
