import { Coordinate } from '@/model/Position'

export class Coord implements Coordinate {
  x: number
  y: number

  static EPSILON_DISTANCE_ZERO = 0.000001

  constructor (x:number,y:number) {
    this.x = x
    this.y = y
  }

  public static fromCoordinate (c: Coordinate): Coord {
    return new Coord(c.x,c.y)
  }

  public static distance (a: Coord, b:Coord): number {
    return Math.sqrt((a.x - b.x) ** 2 + (a.y - b.y) ** 2)
  }

  public distanceTo (other:Coord) : number {
    return Coord.distance(this,other)
  }

  public static normalizedDirection (a:Coord,b:Coord): Coord | undefined {
    const len = Coord.distance(a,b)
    if (len < Coord.EPSILON_DISTANCE_ZERO ) {
      return undefined
    }

    return new Coord((b.x - a.x) / len,(b.y - a.y) / len)
  }
}
