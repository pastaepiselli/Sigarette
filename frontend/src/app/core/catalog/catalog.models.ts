/** Nomi coerenti con i record Java, inclusi logo_url, nicotineMG e ratingCount. */
export interface Brand {
  readonly id: number;
  readonly name: string;
  readonly country: string | null;
  readonly description: string | null;
  readonly logo_url: string | null;
}

export interface Cigarette {
  readonly id: number;
  readonly brand: Brand;
  readonly name: string;
  readonly price: number;
  readonly description: string | null;
  readonly imageUrl: string | null;
  readonly nicotineMG: number | null;
  readonly tar: number | null;
  readonly co: number | null;
  readonly format: string | null;
  readonly sticksPerPack: number | null;
  readonly isMenthol: boolean;
  readonly avgRating: number;
  readonly ratingCount: number;
  /** LocalDateTime del backend: nessun offset timezone garantito. */
  readonly createdAt: string;
}
