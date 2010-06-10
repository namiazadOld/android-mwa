package com.sa.mwa;

import android.os.Handler;
import android.os.RemoteException;

public class GuiNotifyTemperatureChanged extends INotifyTemperatureChanged.Stub
{
	private Handler handler;
	public GuiNotifyTemperatureChanged(Handler handler)
	{
		this.handler = handler;
	}
	
	@Override
	public void temperatureChanged(double value, double humidity)
			throws RemoteException {
		handler.sendMessage(handler.obtainMessage(1, value));
	}

}
