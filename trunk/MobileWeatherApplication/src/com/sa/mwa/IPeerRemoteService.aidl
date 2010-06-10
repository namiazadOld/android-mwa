package com.sa.mwa;

import com.sa.mwa.INotifyTemperatureChanged;

interface IPeerRemoteService 
{
	double retrieveTemparature();
	double retrieveHumidity();
	void registerCallBack(INotifyTemperatureChanged ntc);
	void unregisterCallBack(INotifyTemperatureChanged ntc);
	int  getRegisteredServicesCount();
}
