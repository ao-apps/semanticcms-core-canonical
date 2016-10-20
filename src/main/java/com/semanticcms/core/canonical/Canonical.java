/*
 * semanticcms-core-canonical - Canonical URLs for SemanticCMS pages.
 * Copyright (C) 2016  AO Industries, Inc.
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
 * along with semanticcms-core-canonical.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.semanticcms.core.canonical;

import static com.aoindustries.encoding.TextInXhtmlEncoder.encodeTextInXhtml;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.servlet.Component;
import com.semanticcms.core.servlet.ComponentPosition;
import com.semanticcms.core.servlet.View;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Adds a canonical URL link just before head end.
 * This is applied to all {@link View views} and all {@link Page pages}, even those that are "noindex".
 * Views are included in the canonical URL.
 */
public class Canonical implements Component {

	@Override
	public void doComponent(
		ServletContext servletContext,
		HttpServletRequest request,
		HttpServletResponse response,
		Writer out,
		View view,
		Page page,
		ComponentPosition position
	) throws ServletException, IOException {
		if(
			view != null
			&& page != null
			&& position == ComponentPosition.HEAD_END
		) {
			out.write("<link rel=\"canonical\" href=\"");
			encodeTextInXhtml(view.getCanonicalUrl(servletContext, request, response, page), out);
			out.write("\" />\n");
		}
	}
}
