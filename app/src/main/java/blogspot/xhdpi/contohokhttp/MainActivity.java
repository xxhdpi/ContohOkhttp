package blogspot.xhdpi.contohokhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  private static final String URL_SERVICES = "https://httpbin.org/get";
  private Button btnAmbilData;
  private TextView tvHasil;

  // declare http client
  private OkHttpClient httpClient;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btnAmbilData = (Button) findViewById(R.id.btnAmbilData);
    tvHasil = (TextView) findViewById(R.id.tvResult);

    // inisiasi http client
    httpClient = new OkHttpClient();

    btnAmbilData.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        ambilDataWeb(URL_SERVICES);
      }
    });
  }

  /**
   * ambil data web dengan OkHttp
   */
  private void ambilDataWeb(String url) {
    // create request
    Request request = new Request.Builder().url(url).build();

    // create async request call
    httpClient.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        Log.e(TAG, "onFailure: " + e.getLocalizedMessage());
      }

      @Override public void onResponse(Call call, final Response response) throws IOException {
        final String result = response.body().string();
        Log.d(TAG, "onResponse: " + result);
        // set response hasil to textview
        if (response.isSuccessful()) {
          // pass result to UIThread
          runOnUiThread(new Runnable() {
            @Override public void run() {
              tvHasil.setText(result);
            }
          });
        } else {
          throw new IOException("Unexpected code " + response);
        }
      }
    });
  }
}
