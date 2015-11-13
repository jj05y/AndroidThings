package cmwstudios.shakerapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements ShakerListener.CallbackListener {

    TextView textView;
    ShakerListener shakerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shakerListener == null) {
            textView= (TextView) findViewById(R.id.text1);
            textView.setText("");
            shakerListener = new ShakerListener(this);
            shakerListener.addCallBackListener(this);
        }
        shakerListener.start();
    }

    @Override
    public void thresholdReached() {
        Log.d("thresholdReached", "Threshold has been reached");
        textView.setText("SHAKEN AND BAKEN");
        shakerListener.stop();
        shakerListener = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (shakerListener != null) {
            shakerListener.stop();
        }
    }

}
