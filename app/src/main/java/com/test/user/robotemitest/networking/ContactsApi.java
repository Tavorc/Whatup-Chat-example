package com.test.user.robotemitest.networking;


import com.test.user.robotemitest.networking.request.ContactsRequest;
import com.test.user.robotemitest.networking.response.ContactsResponse;

import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface ContactsApi {
    @POST(ContactsConstant.LOAD)
    Observable<List<ContactsResponse>> getContactList(@Body ContactsRequest contactsRequest);
}
