package com.prekju.fallennymous.prekjutelulasserver.Service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.prekju.fallennymous.prekjutelulasserver.Common.Common;
import com.prekju.fallennymous.prekjutelulasserver.Model.Token;

/**
 * Created by fallennymous on 14/02/2019.
 */

public class MyFirebaseService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String tokenRefreshed = FirebaseInstanceId.getInstance().getToken();
        updateTokenToFirebase(tokenRefreshed);
    }

    private void updateTokenToFirebase(String tokenRefreshed) {
        if (Common.currentUser != null) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference tokens = db.getReference("Tokens");
            Token data = new Token(tokenRefreshed, true); // Server Side Tokens True
            tokens.child(Common.currentUser.getPhone()).setValue(data);
        }
    }
}
