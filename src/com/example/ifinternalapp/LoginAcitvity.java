package com.example.ifinternalapp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginAcitvity extends ActionBarActivity {
	private final String NAMESPACE = "http://ws.IFInternal.org";
	private final String URL = "http://172.28.178.242:8081/IFInternal/services/loginAuthentication?wsdl";
	private final String SOAP_ACTION = "http://ws.IFInternal.org/authentication";
	private final String METHOD_NAME = "authentication";
	private String TAG = "Vik";

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_acitvity);
		Button login = (Button) findViewById(R.id.btn_login);
		login.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				System.out.println("LoginActivity: button clicked");
				new Connection().execute();

			}
		});
	}

	private class Connection extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			Log.i(TAG, "doInBackground");
			return loginAction();
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i(TAG, "onPostExecute");
			TextView res = (TextView) findViewById(R.id.tv_status);
			Log.i(TAG, "find textview");
			Log.i(TAG, result);
			res.setText(result);
		}

	}

	private String loginAction() {
		System.out.println("LoginActivity: loginAction():");
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		EditText userName = (EditText) findViewById(R.id.userId);
		String user_Name = userName.getText().toString();
		EditText userPassword = (EditText) findViewById(R.id.password);
		String user_Password = userPassword.getText().toString();

		// Pass value for userName variable of the web service
		PropertyInfo unameProp = new PropertyInfo();
		unameProp.setName("userName");// Define the variable name in the web
										// service method
		unameProp.setValue(user_Name);// set value for userName variable
		unameProp.setType(String.class);// Define the type of the variable
		request.addProperty(unameProp);// Pass properties to the variable

		// Pass value for Password variable of the web service
		PropertyInfo passwordProp = new PropertyInfo();
		passwordProp.setName("password");
		passwordProp.setValue(user_Password);
		passwordProp.setType(String.class);
		request.addProperty(passwordProp);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {

			androidHttpTransport.call(SOAP_ACTION, envelope);

			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			System.out.println("LoginActivity: response status "
					+ response.toString());
			return response.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed!";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_acitvity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
