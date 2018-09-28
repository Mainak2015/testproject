package com.example.project.myspeechtotext.ui.dashboard;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import retrofit2.Response;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashBoardPresenterTest {
    private DashBoardPresenter presenter;

    @Mock
    private DashboardContainer.View view;
    @Mock
    private DashboardInteractor dashboardInteractor;
    @Mock
    private DashboardContainer.Interactor  interactor;
    @Mock
    Response<ConnectivityManager> response;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void onNetWorkCheck() {
        final Context context = Mockito.mock(Context.class);
        final ConnectivityManager connectivityManager = Mockito.mock(ConnectivityManager.class);
        final NetworkInfo networkInfo = Mockito.mock(NetworkInfo.class);

        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnected()).thenReturn(true);
    }

    @Test
    public void onLayoutUpdateTest(){

    }

}