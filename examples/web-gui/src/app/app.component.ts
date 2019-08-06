import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'web-gui';

  pingCountRoute = '/api/ping';
  pongCountRoute = '/api/pong';
  pingCountRetriever: (res) => number = res => res.ping;
  pongCountRetriever: (res) => number = res => res.pong;
}
