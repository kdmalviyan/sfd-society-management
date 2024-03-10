import { formatDate } from '@angular/common';
export class UserDto {
  id: number;
  img: string;
  fName: string;
  lName: string;
  email: string;
  gender: string;
  bDate: string;
  mobile: string;
  address: string;
  country: string;
  constructor(userDto: UserDto) {
    {
      this.id = userDto.id || this.getRandomID();
      this.img = userDto.img || 'assets/images/user/user1.jpg';
      this.fName = userDto.fName || '';
      this.lName = userDto.lName || '';
      this.email = userDto.email || '';
      this.gender = userDto.gender || 'male';
      this.bDate = formatDate(new Date(), 'yyyy-MM-dd', 'en') || '';
      this.mobile = userDto.mobile || '';
      this.address = userDto.address || '';
      this.country = userDto.country || '';
    }
  }
  public getRandomID(): number {
    const S4 = () => {
      return ((1 + Math.random()) * 0x10000) | 0;
    };
    return S4() + S4();
  }
}
