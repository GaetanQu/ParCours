import { UserDTO } from "./user.dto";
import { SubjectDTO } from "./subject.dto";

export interface SessionDTO {
    id: number;
    label: string;
    beginsAt: string; // ISO string format for LocalDateTime
    endsAt: string; // ISO string format for LocalDateTime
    users: UserDTO[];
    subjects: SubjectDTO[];
}