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

package org.crsh.shell.impl;

import org.crsh.command.CommandContext;
import org.crsh.command.ShellPrinter;
import org.crsh.shell.ShellResponseContext;
import org.crsh.util.LineFeedWriter;

import java.io.StringWriter;
import java.util.*;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
class CommandContextImpl<C, P> implements CommandContext<C, P> {

  /** . */
  private final ShellResponseContext responseContext;

  /** . */
  private final Map<String, Object> state;

  /** . */
  private ShellPrinter writer;

  /** . */
  private StringWriter buffer;

  /** . */
  private List<P> products;

  /** . */
  private Iterable<C> consumedItems;

  public CommandContextImpl(
      ShellResponseContext responseContext,
      Iterable<C> consumedItems,
      Map<String, Object> state) {
    this.state = state;
    this.responseContext = responseContext;
    this.writer = null;
    this.buffer = null;
    this.consumedItems = consumedItems;
    this.products = Collections.emptyList();
  }

  public List<P> getProducedItems() {
    return products;
  }

  public StringWriter getBuffer() {
    return buffer;
  }

  public Iterable<C> consume() {
    return consumedItems;
  }

  public void produce(P product) {
    if (products.isEmpty()) {
      products = new LinkedList<P>();
    }
    products.add(product);
  }

  public ShellPrinter getWriter() {
    if (writer == null) {
      buffer = new StringWriter();
      writer = new ShellPrinter(new LineFeedWriter(buffer, "\r\n"));
    }
    return writer;
  }

  public int size() {
    return state.size();
  }

  public boolean isEmpty() {
    return state.isEmpty();
  }

  public boolean containsKey(Object o) {
    return state.containsKey(o);
  }

  public boolean containsValue(Object o) {
    return state.containsValue(o);
  }

  public Object get(Object o) {
    return state.get(o);
  }

  public Object put(String s, Object o) {
    return state.put(s, o);
  }

  public Object remove(Object o) {
    return state.remove(o);
  }

  public void putAll(Map<? extends String, ? extends Object> map) {
    state.putAll(map);
  }

  public void clear() {
    state.clear();
  }

  public Set<String> keySet() {
    return state.keySet();
  }

  public Collection<Object> values() {
    return state.values();
  }

  public Set<Entry<String, Object>> entrySet() {
    return state.entrySet();
  }

  @Override
  public boolean equals(Object o) {
    return state.equals(o);
  }

  @Override
  public int hashCode() {
    return state.hashCode();
  }

  public String readLine(String msg, boolean echo) {
    if (responseContext != null) {
      return responseContext.readLine(msg, echo);
    } else {
      throw new IllegalStateException("The command does not have access to console line reading");
    }
  }
}
