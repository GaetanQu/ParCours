import { UserDTO } from "../model/user.dto";

export interface LoginResponseDTO {
    token: string;
    user: UserDTO;
}