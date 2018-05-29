package saedc.example.com.LoginPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.stephenvinouze.materialnumberpickercore.MaterialNumberPicker;

import es.dmoral.toasty.Toasty;
import saedc.example.com.R;
import saedc.example.com.View.MainActivity;

public class Login extends AppCompatActivity {
    private Button btn_Next;
    private Button calendarButto;
    private EditText uName;
    private EditText uNumber;
    private String Namee;
    private TextView info;
    String date;
    Intent Intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_Next = (Button) findViewById(R.id.button2);
        calendarButto = (Button) findViewById(R.id.calendarButto);
        uName = (EditText) findViewById(R.id.Name);
        uNumber = (EditText) findViewById(R.id.Number);
        info = (TextView) findViewById(R.id.info);

        //SharedPreferences

        final SharedPreferences shrd = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = shrd.edit();

        String Name = shrd.getString("Settings_name", "");
        if (!Name.isEmpty()) {
            Intent Intent = new Intent(this, MainActivity.class); //To Home Activity
            startActivity(Intent);
            finish();
        }

        //NumberPicker
        calendarButto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialNumberPicker numberPicker = new MaterialNumberPicker(Login.this);
                numberPicker.setTextColor(ContextCompat.getColor(Login.this, R.color.colorPrimary));
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(30);
                numberPicker.setTextSize(100);
                new AlertDialog.Builder(Login.this)
                        .setTitle("يوم الراتب")
                        .setView(numberPicker)
                        .setNegativeButton(getString(android.R.string.cancel), null)
                        .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                date=String.valueOf(numberPicker.getValue());
                            }
                        })
                        .show();

            }
        });

        btn_Next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Namee = String.valueOf((uName.getText().toString()));
                if (Namee.toString().isEmpty() | uNumber.getText().toString().isEmpty() | date==null) { //check input values
                    Toasty.info(Login.this, "هناك خانة فارغة", Toast.LENGTH_SHORT, true).show();

                } else { //input is checked !!
                    editor.putString("Settings_name", uName.getText().toString()); //get The user name
                    editor.putString("Settings_salary",uNumber.getText().toString() );
                    editor.putString("datemonthly",date );
                    editor.commit();

                    Intent = new Intent(Login.this, MainActivity.class); //To Home Activity
                    startActivity(Intent);
                    finish();
                }


            }

        });
    }
}
