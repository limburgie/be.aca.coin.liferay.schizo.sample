package be.aca.coin.liferay.weather.impl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;

import be.aca.coin.liferay.weather.api.Weather;
import be.aca.coin.liferay.weather.api.WeatherService;
import be.aca.coin.liferay.weather.api.WeatherType;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;

@Component(
		immediate = true,
		service = WeatherService.class
)
public class OWMWeatherService implements WeatherService {

	private static final Log LOGGER = LogFactoryUtil.getLog(OWMWeatherService.class);
	private static final float KELVIN_DIFF = 273.15f;

	public Weather getWeather(User user) {
		String city = getUserCity(user);

		if (city == null) {
			return null;
		}

		OWM owm = new OWM("0356798e8b038187a30c42de80f59ef6");

		try {
			CurrentWeather owmWeather = owm.currentWeatherByCityName(city);

			float temperature = owmWeather.getMainData().getTemp().floatValue() - KELVIN_DIFF;
			WeatherType weatherType = getWeatherType(owmWeather.getWeatherList().get(0).getConditionId());

			return new Weather(weatherType, temperature);
		} catch (APIException e) {
			LOGGER.error(e);
		}

		return null;
	}

	private WeatherType getWeatherType(int c) {
		if (c >= 200 && c < 300) {
			return WeatherType.THUNDER;
		}

		if (c >= 300 && c < 600) {
			return WeatherType.RAINY;
		}

		if (c >= 600 && c < 700) {
			return WeatherType.SNOW;
		}

		if (c >= 700 && c < 770) {
			return WeatherType.FOG;
		}

		if (c >= 770 && c < 800 || c >= 900 && c <= 902 || c >= 960) {
			return WeatherType.STORM;
		}

		if (c == 800 || c == 903 || c == 904 || c == 906 || c == 951) {
			return WeatherType.SUNNY;
		}

		if (c >= 801 && c < 900) {
			return WeatherType.CLOUDY;
		}

		if (c == 905 || c >= 952) {
			return WeatherType.WINDY;
		}

		return WeatherType.UNKNOWN;
	}

	private String getUserCity(User user) {
		List<Address> userAddresses = user.getAddresses();

		if (!userAddresses.isEmpty()) {
			String city = userAddresses.get(0).getCity();

			if (city != null && !city.isEmpty()) {
				return city;
			}
		}

		return null;
	}
}