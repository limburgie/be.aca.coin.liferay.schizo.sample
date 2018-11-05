package be.aca.coin.liferay.weather.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liferay.portal.kernel.model.User;

import be.aca.coin.liferay.schizo.api.service.Schizo;
import be.aca.coin.liferay.weather.api.Weather;
import be.aca.coin.liferay.weather.api.WeatherService;
import be.aca.coin.liferay.weather.api.WeatherType;

@Component(
		immediate = true,
		service = WeatherService.class
)
public class PersonaWeatherService implements WeatherService {

	@Reference private Schizo schizo;

	public Weather getWeather(User user) {
		if (schizo.isPersona()) {
			JsonObject dataContext = new Gson().fromJson(schizo.getDataContext(), JsonObject.class);

			JsonObject weather = dataContext.getAsJsonObject("weather");

			if (weather != null) {
				float temperature = weather.get("temperature").getAsFloat();

				return new Weather(WeatherType.UNKNOWN, temperature);
			}
		}

		return null;
	}
}