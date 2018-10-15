package be.aca.coin.liferay.weather.api;

public class Weather {

	private WeatherType weatherType;
	private float temperature;

	public Weather(WeatherType weatherType, float temperature) {
		this.weatherType = weatherType;
		this.temperature = temperature;
	}

	public WeatherType getWeatherType() {
		return weatherType;
	}

	public float getTemperature() {
		return temperature;
	}
}