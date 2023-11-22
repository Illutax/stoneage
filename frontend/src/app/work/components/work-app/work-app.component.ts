import { ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, OnInit } from '@angular/core';
import { WorkRemoteService } from '../../service/work.remote-service';
import { Subscription, timer } from 'rxjs';
import { Work } from '../../model/Work';

@Component({
  selector: 'app-work-app',
  templateUrl: './work-app.component.html',
  styleUrls: ['./work-app.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class WorkAppComponent implements OnInit {
  private readonly UPDATE_INTERVAL_IN_MS = 250;

  private currentWork: Work | null = null;
  private t: Subscription | null = null;

  public finishedWorking = new EventEmitter<null>();
  public startedWorking = new EventEmitter<null>();
  constructor(
    private readonly workRemoteService: WorkRemoteService,
    private readonly cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.getWork().subscribe((maybeWork) => this.setNewWork(maybeWork));
    this.startedWorking.subscribe(() => console.log('started to work'));
    this.finishedWorking.subscribe(() => console.log('finished to work'));
  }

  protected getDuration(): string {
    if (this.currentWork == null) return 'N/A';
    if (this.currentWork.completed) return 'Done';
    const ms = Math.max(this.currentWork.finishing.getTime() - new Date().getTime(), 0);
    if (ms == 0) {
      this.setNewWork(null);
      return this.getDuration();
    }
    return `${ms / 1000} s`;
  }

  private getWork() {
    return this.workRemoteService.getWork();
  }

  protected hasWork() {
    return this.currentWork != null;
  }

  protected startWork() {
    this.startedWorking.emit();
    this.workRemoteService.startWork().subscribe((work) => this.setNewWork(work));
  }

  private setNewWork(it: Work | null) {
    this.currentWork = it;
    if (it != null) {
      this.t = timer(0, this.UPDATE_INTERVAL_IN_MS).subscribe(() => this.cdr.detectChanges());
    } else {
      this.t?.unsubscribe();
      this.finishedWorking.emit();
    }
    this.cdr.detectChanges();
  }
}
