import * as StompLib from 'stompjs/lib/stomp.js';

/** Browser STOMP only — do not import from `'stompjs'` (that bundle includes Node stomp-node). */
export const Stomp = StompLib.Stomp;
