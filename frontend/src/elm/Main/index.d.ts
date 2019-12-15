// WARNING: Do not manually modify this file. It was generated using:
// https://github.com/dillonkearns/elm-typescript-interop
// Type definitions for Elm ports

export namespace Elm {
  namespace Main {
    export interface App {
      ports: {
        tellMeSeconds: {
          subscribe(callback: (data: string) => void): void
        }
        mazesInformationReceived: {
          send(data: any): void
        }
        mazeUpdates: {
          subscribe(callback: (data: string) => void): void
        }
      };
    }
    export function init(options: {
      node?: HTMLElement | null;
      flags: null;
    }): Elm.Main.App;
  }
}