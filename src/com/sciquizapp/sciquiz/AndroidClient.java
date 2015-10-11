package com.sciquizapp.sciquiz;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.sciquizapp.sciquiz.R;
import com.sciquizapp.sciquiz.MenuActivity.SendEmailAsyncTask;

public class AndroidClient extends Activity {

	EditText textOut;
	TextView textIn;
	EditText textIp;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_client);

		textOut = (EditText)findViewById(R.id.textout);
		textIp = (EditText)findViewById(R.id.textip);
		Button buttonSend = (Button)findViewById(R.id.send);
		textIn = (TextView)findViewById(R.id.textin);
		buttonSend.setOnClickListener(buttonSendOnClickListener);
	}

	Button.OnClickListener buttonSendOnClickListener
	= new Button.OnClickListener(){

		@Override
		public void onClick(View arg0) {
			new SendAsyncTask().execute();
		}};
		

		//class for sending mail
		class SendAsyncTask extends AsyncTask <Void, Void, Boolean> {
			@Override
			protected Boolean doInBackground(Void... params) {
				if (BuildConfig.DEBUG) Log.v(SendAsyncTask.class.getName(), "doInBackground()");
				try {
					// TODO Auto-generated method stub
					Socket socket = null;
					DataOutputStream dataOutputStream = null;
					DataInputStream dataInputStream = null;

					try {
						//socket = new Socket(textIp.getText().toString(), 8888);
						socket = new Socket("192.168.81.1", 8888);
						dataOutputStream = new DataOutputStream(socket.getOutputStream());
						dataInputStream = new DataInputStream(socket.getInputStream());
						dataOutputStream.writeUTF(textOut.getText().toString());
						textIn.setText(dataInputStream.readUTF());
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally{
						if (socket != null){
							try {
								socket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						if (dataOutputStream != null){
							try {
								dataOutputStream.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						if (dataInputStream != null){
							try {
								dataInputStream.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
}