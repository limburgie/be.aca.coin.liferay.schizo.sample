package be.aca.coin.liferay.weather.api;

import com.liferay.portal.kernel.model.User;

public interface WeatherService {

	/**
	 * Returns the weather for the given Liferay user.
	 * Return null if the weather can not be retrieved.
	 */
	Weather getWeather(User user);
}