package com.sa.mwa;

import android.os.RemoteException;

public class LogNotifyTemperatureChanged  extends INotifyTemperatureChanged.Stub{

	@Override
	public void temperatureChanged(double value, double humidity)
			throws RemoteException {
		
		
	}

}
