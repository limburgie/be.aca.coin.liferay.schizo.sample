package be.aca.coin.liferay.weather.portlet;

import java.io.IOException;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;

import be.aca.coin.liferay.weather.api.Weather;
import be.aca.coin.liferay.weather.api.WeatherService;

@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.display-category=category.sample",
				"javax.portlet.display-name=Weather Portlet",
				"javax.portlet.init-param.view-template=/view.jsp",
				"javax.portlet.name=be_aca_coin_liferay_weather_portlet_WeatherPortlet",
				"javax.portlet.supports.mime-type=text/html"
		},
		service = Portlet.class
)
public class WeatherPortlet extends MVCPortlet {

	private static final Log LOGGER = LogFactoryUtil.getLog(WeatherPortlet.class);

	@Reference private Portal portal;
	@Reference private WeatherService weatherService;

	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			Weather weather = weatherService.getWeather(portal.getUser(renderRequest));
			renderRequest.setAttribute("weather", weather);
			renderRequest.setAttribute("city", getUserCity(renderRequest));
		} catch (PortalException e) {
			LOGGER.error(e);
		}

		super.doView(renderRequest, renderResponse);
	}

	private String getUserCity(RenderRequest renderRequest) {
		try {
			User user = portal.getUser(renderRequest);
			List<Address> userAddresses = user.getAddresses();

			if (!userAddresses.isEmpty()) {
				return userAddresses.get(0).getCity();
			}
		} catch (PortalException e) {
			LOGGER.error(e);
		}

		return null;
	}
}