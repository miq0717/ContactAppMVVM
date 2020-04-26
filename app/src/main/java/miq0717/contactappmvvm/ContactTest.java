package miq0717.contactappmvvm;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import miq0717.contactappmvvm.db.ContactDatabase;
import miq0717.contactappmvvm.model.Contact;
import miq0717.contactappmvvm.model.ContactDao;


/*Created by MiQ0717 on 25-Apr-2020.*/
public class ContactTest {


    private static JSONArray loadJSONArray(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream inputStream = context.getResources().openRawResource(R.raw.contact_list);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject jsonObject = new JSONObject(builder.toString());
            return jsonObject.getJSONArray("contacts");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void fillWithStartingData(Context context) {
        ContactDao contactDao = ContactDatabase.Companion.getDatabase(context).contactDao();
        JSONArray contacts = loadJSONArray(context);
        try {
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);

                String contactName = contact.getString("name");
                String contactNumber = contact.getString("phone");
                contactDao.insert(new Contact(contactName,contactNumber));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
