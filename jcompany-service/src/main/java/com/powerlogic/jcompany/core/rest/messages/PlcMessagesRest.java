package com.powerlogic.jcompany.core.rest.messages;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.LocaleUtils;

import com.powerlogic.jcompany.core.messages.PlcMessageEntry;
import com.powerlogic.jcompany.core.messages.PlcMessageMap;
import com.powerlogic.jcompany.core.messages.PlcMessageTranslator;
import com.powerlogic.jcompany.core.messages.PlcMessageType;
import com.powerlogic.jcompany.core.rest.auth.PlcNotAuthenticated;

@PlcNotAuthenticated
@Path("/mensagens")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class PlcMessagesRest
{
	@Inject
	private PlcMessageTranslator messageTranslator;

	@GET
	public PlcMessageMap getMensagens(@Context HttpServletRequest request,  @Context UriInfo ui)
	{
		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
		String locale = queryParams.getFirst("locale");
		Locale l = null;
		if (locale!=null && !locale.equals("")) {
			l = LocaleUtils.toLocale(locale);
		} else {
			l = request.getLocale();
		}
		return messageTranslator.getAllMessages(l);
	}


	@GET
	@Path("/i18n")
	public Map<String, String> getMensagensI18n(@Context HttpServletRequest request,  @Context UriInfo ui)
	{

		Map<String, String> mapaConvertido = new HashMap<String, String>();

		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
		String locale = queryParams.getFirst("locale");

		if (locale!=null && !locale.equals("")) {
			Locale l = LocaleUtils.toLocale(locale);
			if (l!=null) {
				PlcMessageMap mapa =  messageTranslator.getAllMessages(l);
				if (mapa!=null) {
					Map<PlcMessageType, List<PlcMessageEntry>> mapaMensagens = mapa.getMessages();
					if (mapaMensagens!=null && mapaMensagens.get(PlcMessageType.INFO)!=null) {
						List<PlcMessageEntry> listaMensagens = mapaMensagens.get(PlcMessageType.INFO);
						for(PlcMessageEntry mensagem : listaMensagens) {
							mapaConvertido.put(mensagem.getKey().getName(), mensagem.getMessage());
						}
					}
				}
			}
		}

		return mapaConvertido;
	}

}
