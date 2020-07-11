export interface IAdjustTutorial {
  id?: number;
  title?: string;
  description?: string;
  thumbnailContentType?: string;
  thumbnail?: any;
  tokenPrice?: number;
  contentContentType?: string;
  content?: any;
  clientId?: number;
}

export const defaultValue: Readonly<IAdjustTutorial> = {};
