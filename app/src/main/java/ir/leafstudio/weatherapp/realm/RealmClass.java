package ir.leafstudio.weatherapp.realm;

import android.content.Context;

import io.realm.Realm;

public class RealmClass {
    RealmClass(Context context){
         Realm.init(context);
    }
    public Realm setUpRealm() {
        Realm realm = Realm.getDefaultInstance();
      /**
       Realm myOtherRealm = Realm.getInstance( realmConfiguration );
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("books.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        */
        return realm;
     }
}
