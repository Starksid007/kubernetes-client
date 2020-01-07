/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fabric8.kubernetes.client.utils;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;

import java.util.Objects;

/**
 * A utility class to enable and disable callbacks to a watcher instance.
 */
public class WatcherToggle<T extends HasMetadata> implements Watcher<T> {

  private Watcher<T> delegate;

  private boolean enabled;

  public WatcherToggle(Watcher<T> delegate, boolean enabled) {
    this.delegate = Objects.requireNonNull(delegate, "delegate watcher cannot be null");
    this.enabled = enabled;
  }

  public void disable() {
    this.enabled = false;
  }

  public void enable() {
    this.enabled = true;
  }

  @Override
  public void eventReceived(Action action, T resource) {
    if (enabled) {
      delegate.eventReceived(action, resource);
    }
  }

  @Override
  public void onClose(KubernetesClientException cause) {
    if (enabled) {
      delegate.onClose(cause);
    }
  }
}
