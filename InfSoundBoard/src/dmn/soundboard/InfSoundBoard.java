package dmn.soundboard;

import android.app.ListActivity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.*;


public class InfSoundBoard extends ListActivity {
	/** Called when the activity is first created. */


	// declare our MediaPlayer, Toast and AssetFileDescriptor objects
	MediaPlayer mp;
	Toast toast;
	AssetFileDescriptor file;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  
		getListView().setCacheColorHint(0);
		getListView().setBackgroundResource(R.drawable.bg);
		
	

		// init our mediaplayer and toast objects
		mp = new MediaPlayer();

		toast = Toast.makeText( getApplicationContext()  , "" , Toast.LENGTH_SHORT );
		toast.setGravity(Gravity.BOTTOM, 0, 0);

		try
		{

			// retrieve list of all phrases from res/values/strings.xml
			String[] PHRASES = getResources().getStringArray(R.array.phrases_array);

			// load the PHRASES retrieved from strings.xml into the arrayAdapter for this ListActivity
			ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.list_item, PHRASES);

			setListAdapter(myAdapter);
			ListView lv = getListView();


			// when the user clicks on an item, trigger correct sample based on position
			lv.setOnItemClickListener(new OnItemClickListener() 
			{
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
				{

					try
					{

						// if we have clicked on a new sample while the old one is still playing, stop it first.
						if (mp.isPlaying())	
						{
							mp.stop();
						}

						// reset state of mediaplayer
						mp.reset();

						// load and play new sample
						file = getAssets().openFd("phrases/" + position + ".ogg");
						mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
						mp.prepare();
						mp.start();

						// pop up the accompanying toast text that shows the item text clicked on by the user in the TextView 
						toast.setText(((TextView) view).getText());
						toast.show();

					}

					// deal with the exception
					catch (Exception e) 
					{
						Toast.makeText(getApplicationContext(), "Error in onItemClick: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
						Log.d("InfSB", "Error in onItemClick: "+e.getLocalizedMessage());
					}
				}
			});
		}

		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), "Error in onCreate: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
			Log.d("InfSB", "Error in onCreate: "+e.getLocalizedMessage());
		}
	}

}