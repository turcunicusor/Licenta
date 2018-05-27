import {AfterViewInit, Component, ElementRef, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {BackendService} from '../../backendservice/backend.service';
import {HttpErrorResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';
import * as Highcharts from 'highcharts';

@Component({
  selector: 'app-lamp',
  templateUrl: './lamp.component.html',
  styleUrls: ['./lamp.component.css']
})

export class LampComponent implements OnInit, AfterViewInit, OnDestroy {
  chart: Highcharts.ChartObject;
  isSliced: Boolean;
  defaults: any;
  @Input() deviceId: string;
  @Input() params: {};

  constructor(public _bs: BackendService, private toastr: ToastrService) {
    this.isSliced = true;
    //               red    yellow green  aqua   blue   violet
    this.defaults = [false, false, false, false, false, false];
  }

  ngOnInit() {
    this.initDefaults(this.params['red'], this.params['green'], this.params['blue']);
  }

  ngOnDestroy() {
    this.chart.destroy();
  }

  initDefaults(red, green, blue) {
    red = red === 'true';
    green = green === 'true';
    blue = blue === 'true';
    if (red && !green && !blue) {
      this.defaults[0] = true;
    } else if (!red && green && !blue) {
      this.defaults[2] = true;
    } else if (!red && !green && blue) {
      this.defaults[4] = true;
    } else if (red && green && !blue) {
      this.defaults[1] = true;
    } else if (!red && green && blue) {
      this.defaults[3] = true;
    } else if (red && !green && blue) {
      this.defaults[5] = true;
    }
  }

  unsliceElements() {
    for (const data of this.chart.series[0].data) {
      data.slice(false);
    }
  }

  onClick(event) {
    if (this.isSliced) {
      this.unsliceElements();
      this.isSliced = false;
    }
    const state = event.point.state !== 'select';
    const params = this.colorSelected(event.point.name, state);
    this._bs.setParams(this.deviceId, params).subscribe(
      res => {
        if (state === true) {
          this.toastr.success('State \'' + event.point.name + '\' updated successfully.');
        }
      },
      (err: HttpErrorResponse) => {
        const message = this._bs.handleError(err);
        this.toastr.warning(message);
      });
  }

  colorSelected(color, state) {
    const params = {'red': false, 'green': false, 'blue': false};
    switch (color) {
      case 'red':
        params[color] = state;
        break;
      case 'yellow':
        params['red'] = state;
        params['green'] = state;
        break;
      case 'green':
        params[color] = state;
        break;
      case 'aqua':
        params['green'] = state;
        params['blue'] = state;
        break;
      case 'blue':
        params[color] = state;
        break;
      case 'violet':
        params['blue'] = state;
        params['red'] = state;
        break;
      default:
        break;
    }
    return params;
  }

  ngAfterViewInit(): void {
    const pieceSize = 100 / 6;
    const data = [
      {
        name: 'red',
        color: 'rgba(255, 0, 0, 1)',
      },
      {
        name: 'yellow',
        color: 'rgba(255, 255, 0, 1)',
      },
      {
        name: 'green',
        color: 'rgba(0, 255, 0, 1)',
      },
      {
        name: 'aqua',
        color: 'rgba(0, 255, 255, 1)',
      },
      {
        name: 'blue',
        color: 'rgba(0, 0, 255, 1)',
      },
      {
        name: 'violet',
        color: 'rgba(255, 0, 255, 1)',
      }
    ];
    data.forEach((item, index) => {
      data[index]['sliced'] = this.defaults[index];
      data[index]['y'] = pieceSize;
    });
    const opts: any = {
      chart: {
        type: 'pie',
        renderTo: 'chartTarget',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        backgroundColor: 'rgba(255, 255, 255, 0.0)',
      },
      title: {
        text: 'Lamp',
        x: -20 // center
      },
      tooltip: {
        pointFormatter: function () {
          const point = this;
          return '<span style="color:' + this.color + '">\u25CF</span> '
            + '<b>' + (this.sliced > 0 ? 'On' : 'off') + '</b><br/>';
        }
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {enabled: true}
        }
      },
      series: [{
        data: data,
        point: {
          events: {click: this.onClick.bind(this)}
        }
      }],
      credits: {
        enabled: false
      },
    };
    this.chart = new Highcharts.Chart(opts);
  }
}
