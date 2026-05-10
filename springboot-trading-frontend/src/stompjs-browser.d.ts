/**
 * Explicit path avoids stompjs/index.js pulling in stomp-node.js → require('websocket').
 */
declare module 'stompjs/lib/stomp.js' {
  import type { Client } from 'stompjs';

  export const Stomp: {
    over(socket: unknown): Client;
    client(url: string, protocols?: string | string[]): Client;
  };
}
