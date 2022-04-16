/*
 * semanticcms-core-canonical - Canonical URLs for SemanticCMS pages.
 * Copyright (C) 2016, 2017, 2019, 2020, 2021, 2022  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of semanticcms-core-canonical.
 *
 * semanticcms-core-canonical is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * semanticcms-core-canonical is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with semanticcms-core-canonical.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.semanticcms.core.canonical;

import com.aoapps.html.any.AnyLINK;
import com.aoapps.html.servlet.DocumentEE;
import com.aoapps.net.URIEncoder;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.renderer.html.Component;
import com.semanticcms.core.renderer.html.ComponentPosition;
import com.semanticcms.core.renderer.html.HtmlRenderer;
import com.semanticcms.core.renderer.html.View;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Adds a canonical URL link just before head end.
 * This is applied to all {@link View views} and all {@link Page pages}, even those that are "noindex".
 * Views are included in the canonical URL.
 */
public final class Canonical implements Component {

	@WebListener("Registers the Canonical component in HtmlRenderer.")
	public static class Initializer implements ServletContextListener {
		@Override
		public void contextInitialized(ServletContextEvent event) {
			HtmlRenderer.getInstance(event.getServletContext()).addComponent(new Canonical());
		}
		@Override
		public void contextDestroyed(ServletContextEvent event) {
			// Do nothing
		}
	}

	private Canonical() {
		// Do nothing
	}

	@Override
	public void doComponent(
		ServletContext servletContext,
		HttpServletRequest request,
		HttpServletResponse response,
		DocumentEE document,
		View view,
		Page page,
		ComponentPosition position
	) throws ServletException, IOException {
		if(
			view != null
			&& page != null
			&& position == ComponentPosition.HEAD_END
		) {
			document.link(AnyLINK.Rel.CANONICAL)
				.href(
					// Write US-ASCII always per https://datatracker.ietf.org/doc/html/rfc6596#section-3
					URIEncoder.encodeURI(
						view.getCanonicalUrl(servletContext, request, response, page)
					)
			).__();
		}
	}
}
