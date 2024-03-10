import { MAT_DIALOG_DATA, MatDialogRef, MatDialogContent, MatDialogClose } from '@angular/material/dialog';
import { Component, Inject } from '@angular/core';
import { UntypedFormControl, Validators, UntypedFormGroup, UntypedFormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DATE_LOCALE, MatOptionModule } from '@angular/material/core';
import { formatDate } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { UserService } from 'app/services/user.service';
import { UserDto } from '../../user.modal';

export interface DialogData {
  id: number;
  action: string;
  userDto: UserDto;
}

@Component({
    selector: 'app-form-dialog',
    templateUrl: './form-dialog.component.html',
    styleUrls: ['./form-dialog.component.scss'],
    providers: [{ provide: MAT_DATE_LOCALE, useValue: 'en-GB' }],
    standalone: true,
    imports: [
        MatButtonModule,
        MatIconModule,
        MatDialogContent,
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatRadioModule,
        MatDatepickerModule,
        MatSelectModule,
        MatOptionModule,
        MatDialogClose,
    ],
})
export class FormDialogComponent {
  action: string;
  dialogTitle: string;
  userForm: UntypedFormGroup;
  userDto: UserDto;
  constructor(
    public dialogRef: MatDialogRef<FormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    public userService: UserService,
    private fb: UntypedFormBuilder
  ) {
    // Set the defaults
    this.action = data.action;
    if (this.action === 'edit') {
      this.dialogTitle =
        data.userDto.fName + ' ' + data.userDto.lName;
      this.userDto = data.userDto;
    } else {
      this.dialogTitle = 'New Record';
      const blankObject = {} as UserDto;
      this.userDto = new UserDto(blankObject);
    }
    this.userForm = this.createContactForm();
  }
  formControl = new UntypedFormControl('', [
    Validators.required,
    // Validators.email,
  ]);
  getErrorMessage() {
    return this.formControl.hasError('required')
      ? 'Required field'
      : this.formControl.hasError('email')
        ? 'Not a valid email'
        : '';
  }
  createContactForm(): UntypedFormGroup {
    return this.fb.group({
      id: [this.userDto.id],
      img: [this.userDto.img],
      fName: [this.userDto.fName, [Validators.required]],
      lName: [this.userDto.lName, [Validators.required]],
      email: [
        this.userDto.email,
        [Validators.required, Validators.email, Validators.minLength(5)],
      ],
      gender: [this.userDto.gender],
      bDate: [
        formatDate(this.userDto.bDate, 'yyyy-MM-dd', 'en'),
        [Validators.required],
      ],
      address: [this.userDto.address],
      mobile: [this.userDto.mobile, [Validators.required]],
      country: [this.userDto.country],
    });
  }
  submit() {
    // emppty stuff
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
  public confirmAdd(): void {
    this.userService.addAdvanceTable(
      this.userForm.getRawValue()
    );
  }
}
