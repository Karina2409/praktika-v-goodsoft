import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SessionStorageService {
  /**
   * Set value in localStorage
   * @param key key <string>
   * @param value value <unknown>
   */
  public set(key: string, value: unknown): void {
    void this;
    sessionStorage.setItem(key, JSON.stringify(value));
  }

  /**
   * Get value from localStorage
   * @param key key <string>
   * @returns value or null if key not found
   */
  public get(key: string): unknown {
    void this;
    const value = sessionStorage.getItem(key);
    return value ? JSON.parse(value) : null;
  }

  /**
   * Remove value from localStorage
   * @param key key <string>
   */
  public remove(key: string): void {
    void this;
    sessionStorage.removeItem(key);
  }

  /**
   * Check if the value exists in the localStorage
   * @param key key <string>
   * @returns true, if value exists, false if value not exists
   */
  public has(key: string): boolean {
    void this;
    return sessionStorage.getItem(key) !== null;
  }

  /**
   * DANGER!, clear the localStorage
   */
  public clear(): void {
    void this;
    sessionStorage.clear();
  }
}
