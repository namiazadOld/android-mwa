package com.sa.mwa;

import java.util.List;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class PeerServiceConnector implements ServiceConnection {

	
	IPeerRemoteService remoteService;
	List<INotifyTemperatureChanged> registeredServices;
	
	public PeerServiceConnector(List<INotifyTemperatureChanged> registeredServices)
	{
		this.registeredServices = registeredServices;
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		remoteService = IPeerRemoteService.Stub.asInterface(service);
		try 
		{
			for (INotifyTemperatureChanged notifyTemperatureChanged : registeredServices)
				remoteService.registerCallBack(notifyTemperatureChanged);
        } 
		catch (RemoteException e) 
        {
			
        }
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		try 
		{
			for (INotifyTemperatureChanged notifyTemperatureChanged : registeredServices)
				remoteService.unregisterCallBack(notifyTemperatureChanged);
        } 
		catch (RemoteException e) 
        {
			
        }
	}
}
