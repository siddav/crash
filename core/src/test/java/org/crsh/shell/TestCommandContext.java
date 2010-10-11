/*
 * Copyright (C) 2010 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.crsh.shell;

import org.crsh.command.CommandContext;
import org.crsh.command.ShellCommand;
import org.crsh.command.ShellPrinter;
import org.crsh.util.LineFeedWriter;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestCommandContext<C, P> extends HashMap<String, Object> implements CommandContext<C, P> {

  /** . */
  private StringWriter buffer;

  /** . */
  private ShellPrinter writer;

  /** . */
  private LinkedList<P> products;

  public String readLine(String msg, boolean echo) {
    throw new UnsupportedOperationException();
  }

  public ShellPrinter getWriter() {
    if (writer == null) {
      writer = new ShellPrinter(new LineFeedWriter(buffer = new StringWriter(), "\r\n"));
    }
    return writer;
  }

  public Iterable<C> consume() {
    throw new UnsupportedOperationException();
  }

  public void produce(P product) {
    if (products == null) {
      products = new LinkedList<P>();
    }
    products.add(product);
  }

  public List<P> getProducts() {
    return products;
  }

  public String execute(ShellCommand<C, P> command, String... args) {
    if (buffer != null) {
      buffer.getBuffer().setLength(0);
    }
    command.execute(this, args);
    return buffer != null ? buffer.toString() : null;
  }
}
