import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UnsubscribeOnDestroyAdapter } from '../shared/UnsubscribeOnDestroyAdapter';
import { UserDto } from 'app/components/user/user.modal';

@Injectable({
  providedIn: 'root'
})
export class UserService  extends UnsubscribeOnDestroyAdapter {
  private readonly API_URL = 'assets/data/advanceTable.json';
  isTblLoading = true;
  dataChange: BehaviorSubject<UserDto[]> = new BehaviorSubject<
    UserDto[]
  >([]);
  // Temporarily stores data from dialogs
  dialogData!: UserDto;
  constructor(private httpClient: HttpClient) {
    super();
  }
  get data(): UserDto[] {
    return this.dataChange.value;
  }
  getDialogData() {
    return this.dialogData;
  }
  /** CRUD METHODS */
  getAllAdvanceTables(): void {
    this.subs.sink = this.httpClient
      .get<UserDto[]>(this.API_URL)
      .subscribe(
        (data) => {
          this.isTblLoading = false;
          this.dataChange.next(data);
        },
        (error: HttpErrorResponse) => {
          this.isTblLoading = false;
          console.log(error.name + ' ' + error.message);
        }
      );
  }
  addAdvanceTable(advanceTable: UserDto): void {
    this.dialogData = advanceTable;

    /*  this.httpClient.post(this.API_URL, advanceTable).subscribe(data => {
      this.dialogData = advanceTable;
      },
      (err: HttpErrorResponse) => {
     // error code here
    });*/
  }
  updateAdvanceTable(advanceTable: UserDto): void {
    this.dialogData = advanceTable;

    /* this.httpClient.put(this.API_URL + advanceTable.id, advanceTable).subscribe(data => {
      this.dialogData = advanceTable;
    },
    (err: HttpErrorResponse) => {
      // error code here
    }
  );*/
  }
  deleteAdvanceTable(id: number): void {
    console.log(id);

    /*  this.httpClient.delete(this.API_URL + id).subscribe(data => {
      console.log(id);
      },
      (err: HttpErrorResponse) => {
         // error code here
      }
    );*/
  }
}
