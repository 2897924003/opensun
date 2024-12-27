import { defineStore } from '#q-app/wrappers';
import { createPinia } from 'pinia';
import type { Router } from 'vue-router';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import { usePersist } from 'pinia-use-persist';
/*
 * When adding new properties to stores, you should also
 * extend the `PiniaCustomProperties` interface.
 * @see https://pinia.vuejs.org/core-concepts/plugins.html#typing-new-store-properties
 */
declare module 'pinia' {
  export interface PiniaCustomProperties {
    readonly router: Router;
  }
}

/*
 * If not building with SSR mode, you can
 * directly export the Store instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Store instance.
 */

export default defineStore((/* { ssrContext } */) => {
  const pinia = createPinia();

  // 在这里添加Pinia插件
  // pinia.use(SomePiniaPlugin)
  pinia.use(piniaPluginPersistedstate);
  pinia.use(usePersist);
  return pinia;
});