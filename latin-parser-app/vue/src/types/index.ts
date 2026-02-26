export interface ParsedData {
  partOfSpeech?: string
  declension?: string
  gender?: string
  case?: string
  number?: string
  stemType?: string
  person?: string
  tense?: string
  voice?: string
  mood?: string
}

export interface ParseResponse {
  success: boolean
  message: string
  inflections: ParsedData[]
}
